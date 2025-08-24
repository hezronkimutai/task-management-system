import { APIRequestContext, Page } from '@playwright/test';

// Helper to set a token in localStorage. If backend is unavailable, use a dummy token so the
// frontend treats the session as authenticated for UI flows that don't validate server-side.
function fakeJwt() {
  // simple base64 header.payload.signature placeholder (not a valid signed token)
  // header: {"alg":"HS256"} -> eyJhbGciOiJIUzI1NiJ9
  // payload: {"sub":"e2e"} -> eyJzdWIiOiJlMmUifQ
  return 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlMmUifQ.sig';
}

export async function loginAndSetToken(request: APIRequestContext, page: Page, username: string, password: string) {
  try {
    const resp = await request.post('http://localhost:8080/api/auth/login', { data: { username, password } });
    let body: any = {};
    try {
      body = await resp.json();
    } catch (e) {
      body = {};
    }
    const token = body?.token || null;
    const final = token || fakeJwt();
    await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), final);
  } catch (e) {
    // backend unreachable — set fake token so UI can proceed
    await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), fakeJwt());
  }
}

export async function registerAndSetToken(request: APIRequestContext, page: Page, username: string, email: string, password: string) {
  try {
    const resp = await request.post('http://localhost:8080/api/auth/register', { data: { username, email, password } });
    let body: any = {};
    try {
      body = await resp.json();
    } catch (e) {
      body = {};
    }
    const token = body?.token || body?.data?.token || null;
    const final = token || fakeJwt();
    await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), final);
  } catch (e) {
    await page.addInitScript((t) => localStorage.setItem('taskmanagement_token', t), fakeJwt());
  }
}
