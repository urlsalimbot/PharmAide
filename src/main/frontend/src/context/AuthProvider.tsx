import {
  createContext,
  Dispatch,
  ReactElement,
  SetStateAction,
  useDebugValue,
  useState,
} from "react";

export type Auth = {
  username: string;
  role: string;
  loggedin: boolean;
};

interface AuthContextInterface {
  auth: Auth;
  setAuth: Dispatch<SetStateAction<Auth>>;
}

const defauth: Auth = {
  username: "",
  role: "",
  loggedin: false,
};

const AuthContext = createContext<AuthContextInterface>({
  auth: {
    username: "",
    role: "",
    loggedin: false,
  },
  setAuth: () => {},
});

interface AuthProviderProps {
  children: ReactElement;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [auth, setAuth] = useState<Auth>(defauth);
  useDebugValue(auth);
  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
