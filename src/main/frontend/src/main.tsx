import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";

// page imports
import App from "./pages/App.tsx";
import PageNotFound from "./pages/PageNotFound.tsx";
import Profile from "./pages/Profile.tsx";
import Market from "./pages/Market.tsx";
import BottomBar from "./pages/BottomBar.tsx";
import Login from "./pages/Login.tsx";
import Register from "./pages/Register.tsx";
import Admin from "./pages/Admin.tsx";

//redux
import { Provider } from "react-redux";
import { store } from "./store/store.tsx";

//Authentication Context
import { AuthProvider } from "./context/AuthProvider.tsx";
import RequireAuth from "./pages/RequireAuth.tsx";

//BrowserRouter Init
const router = createBrowserRouter(
  [
    {
      path: "/",
      element: <Login />,
    },
    {
      path: "/register",
      element: <Register />,
    },
    //Protected Rpute goes here
    {
      path: "/app",
      element: (
        <BottomBar>
          <RequireAuth />
        </BottomBar>
      ),
      children: [
        {
          element: <App />,
          index: true,
        },
        {
          path: "/app/profile",
          element: <Profile />,
        },
        {
          path: "/app/store",
          element: <Market />,
        },
        {
          path: "/app/admin",
          element: <Admin />,
        },
      ],
      errorElement: <PageNotFound />,
    },
    {
      path: "*",
      element: <PageNotFound />,
    },
  ],
  {
    future: {
      v7_relativeSplatPath: true,
      v7_normalizeFormMethod: true,
      v7_fetcherPersist: true,
    },
  }
);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <main className="absolute bg-slate-200 w-full h-full">
      <AuthProvider>
        <Provider store={store}>
          <RouterProvider
            router={router}
            future={{ v7_startTransition: true }}
          />
        </Provider>
      </AuthProvider>
    </main>
  </StrictMode>
);
