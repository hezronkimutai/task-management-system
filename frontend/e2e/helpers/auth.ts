import { APIRequestContext, Page } from '@playwright/test';

export async function loginAndSetToken(request: APIRequestContext, page: Page, username: string, password: string) {
  const resp = await request.post('http://localhost:8080/api/auth/login', { data: { username, password } });
  const body = await resp.json();
  const token = body?.token;
  if (!token) throw new Error('loginAndSetToken: no token returned');
  await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), token);
}

export async function registerAndSetToken(request: APIRequestContext, page: Page, username: string, email: string, password: string) {
  const resp = await request.post('http://localhost:8080/api/auth/register', { data: { username, email, password } });
  const body = await resp.json();
  const token = body?.token;
  if (!token) throw new Error('registerAndSetToken: no token returned');
  await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), token);
}
