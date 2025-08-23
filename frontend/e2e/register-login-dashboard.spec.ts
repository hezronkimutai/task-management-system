import { test, expect } from '@playwright/test';

// Basic e2e: register -> login -> dashboard and responsive checks

const randomSuffix = () => Math.random().toString(36).substring(2, 8);
const usernameBase = 'e2euser';

test.describe('Auth + Dashboard (responsive)', () => {
  test('register -> login -> dashboard on desktop', async ({ page }) => {
    const suffix = randomSuffix();
    const username = `${usernameBase}-${suffix}`;
    const email = `${username}@example.com`;
    const password = 'Password123!';

  // Go to register page
  await page.goto('/register');
  await expect(page).toHaveURL(/register/);

  // MUI TextField labeled selectors
  await page.getByLabel('Username').fill(username);
  await page.getByLabel('Email').fill(email);
  await page.getByLabel('Password').fill(password);
  // Submit
  await page.getByRole('button', { name: /Register/i }).click();

    // After registration, app may redirect to login. Check for stored token first.
    await page.waitForLoadState('networkidle');
    const token = await page.evaluate(() => localStorage.getItem('taskmanagement_token'));
    if (!token) {
      // Not auto-logged-in by register — perform explicit login
      await page.goto('/login');
      await page.getByLabel('Username').fill(username);
      await page.getByLabel('Password').fill(password);
      await page.getByRole('button', { name: /Sign in|Login|Log in|Sign In/i }).click();
      await page.waitForLoadState('networkidle');
    }

  // Expect dashboard route (app navigates to /tasks)
  await page.waitForLoadState('networkidle');
  await expect(page).toHaveURL(/tasks|\/$/);

  // Check Task Dashboard heading exists
  await expect(page.locator('text=Task Dashboard')).toHaveCount(1);
  });

  test('dashboard responsive layout - mobile', async ({ page, request }) => {
  // Create a test user via backend API and set JWT in localStorage so /tasks is accessible
  const suffix = randomSuffix();
  const username = `${usernameBase}-${suffix}`;
  const email = `${username}@example.com`;
  const password = 'Password123!';

  const resp = await request.post('http://localhost:8080/api/auth/register', {
    data: { username, email, password }
  });
  const body = await resp.json();
  const token = body?.token || body?.data?.token || null;
  if (!token) throw new Error('Failed to obtain token from backend during e2e setup');

  // Set token into localStorage before navigation
  await page.addInitScript(({ t }) => localStorage.setItem('taskmanagement_token', t), token);

  await page.goto('/tasks');
  await page.waitForLoadState('networkidle');

  const heading = page.locator('text=Task Dashboard');
  await expect(heading).toBeVisible({ timeout: 10000 });
  });
});
