Playwright e2e tests

Setup

1. Install dev deps:

```bash
npm install
npm run test:e2e:install
```

2. Start the frontend app (and ensure backend is running at http://localhost:8080):

```bash
npm start
```

3. Run tests (headed for interactive):

```bash
npm run test:e2e:headed
```

Or run headless:

```bash
npm run test:e2e
```

Notes
- Tests assume the frontend runs at http://localhost:3000 and backend at http://localhost:8080.
- The tests perform basic register/login/dash checks and simple mobile layout assertions. Adjust selectors to match your UI.
