# TripSync AI Frontend

React + TypeScript + Vite frontend for the simplified TripSync AI backend.

## Included screens

- Signup and login
- Dashboard and trips list
- Create trip and trip overview
- Trip itinerary management
- Expense entry, group balances and settlements
- AI trip planner, budget and expense assistance
- External train, flight and hotel search links
- Notifications

## Run locally

Requirements: Node.js 20+ and npm.

```bash
npm install
cp .env.example .env
npm run dev
```

Frontend: http://localhost:5173

Backend expected at http://localhost:8080. Set `VITE_API_URL` and `VITE_WS_URL` in `.env` if needed.

## Build

```bash
npm run build
```

## Backend contract

This frontend targets the simplified TripSync backend endpoints documented in the backend README. JWT is stored in localStorage and sent as a Bearer token. The chat WebSocket dependency is included for the next iteration, but this version uses the backend REST message endpoints only.

## Notes

- Travel search currently opens external provider search pages. It does not promise live availability inside TripSync.
- AI output is shown as plain text. The backend remains responsible for money calculations and access checks.
- This frontend is a complete first-pass app for the backend endpoints, but should be tested against the running backend and polished after real API responses are confirmed.
