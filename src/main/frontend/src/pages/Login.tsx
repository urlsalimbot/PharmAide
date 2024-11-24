import { AxiosError } from "axios";

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import useAuth from "../hooks/useAuth";

const LOGIN_URL = "/login";

export default function Login() {
  const { setAuth } = useAuth();
  const [username, setUser] = useState<string>("");
  const [password, setPass] = useState<string>("");

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await api().post(
        LOGIN_URL,
        JSON.stringify({ username, password }),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      console.log(JSON.stringify(response?.data));
      //console.log(JSON.stringify(response));
      const role = response?.data?.role;
      setAuth({ username, role, loggedin: true });
      setUser("");
      setPass("");

      navigate("/app");
    } catch (err) {
      if (err instanceof AxiosError) {
        if (!err?.response) {
          // setErrMsg('No Server Response');
        } else if (err.response?.status === 400) {
          // setErrMsg('Missing Username or Password');
        } else if (err.response?.status === 401) {
          // setErrMsg('Unauthorized');
        } else {
          // setErrMsg('Login Failed');
        }
      } else {
        console.log(err);
      }
    }
  };
  return (
    <div className="w-screen h-screen items-center justify-center flex">
      <div className="font-serif flex flex-col w-1/5 min-w-64 h-1/4 min-h-96">
        <form
          className="font-serif flex flex-col min-w-64 h-1/4 min-h-96 rounded-md bg-slate-300"
          onSubmit={handleSubmit}
        >
          <span className=" text-center pt-5 text-xl font-semibold">
            Log In
          </span>
          <span className="p-2 pb-0 ml-3">Username</span>
          <div className="mb-2 flex justify-center items-center">
            <input
              id="username"
              className="rounded-md px-2 w-11/12 flex justify-center items-center"
              type="text"
              value={username}
              onChange={(e) => setUser(e.target.value)}
            />
          </div>
          <span className="block p-2 pb-0 ml-3">Password</span>
          <div className="mb-4 flex justify-center items-center">
            <input
              id="password"
              className="rounded-md px-2 w-11/12"
              type="password"
              value={password}
              onChange={(e) => setPass(e.target.value)}
            />
          </div>
          <div className="mt-auto mb-2 flex justify-center">
            <button className="text-white w-11/12 h-[38px] align-bottom bg-slate-800 rounded-md ">
              Login
            </button>
          </div>
        </form>
        <div className=" flex justify-center">
          <Link to={"/register"} className="mt-2 text-green-400 text-center">
            Don't have an account? Sign up
          </Link>
        </div>
      </div>
    </div>
  );
}
