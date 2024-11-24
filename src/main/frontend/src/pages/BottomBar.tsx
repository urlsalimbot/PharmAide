import { Link } from "react-router-dom";
import user from "../assets/user.svg";
import search from "../assets/search.svg";
import home from "../assets/home.svg";
import { ProductCart } from "./ProductCart";
import { ReactElement } from "react";

interface bbProps {
  children: ReactElement;
}

export default function BottomBar({ children }: bbProps) {
  return (
    <>
      <ProductCart />
      {children}
      <nav className="bg-slate-300 w-screen flex align-middle content-center justify-center fixed z-30 bottom-0">
        <Link
          className="rounded-md  m-1 w-1/6 p-2 bg-slate-400 hover:bg-slate-500"
          to="/app/store"
        >
          <img className="justify-self-center align-middle" src={search} />
        </Link>
        <Link
          className="rounded-md  m-1 w-1/6 p-2 bg-slate-400 hover:bg-slate-500"
          to="/app"
        >
          <img className="justify-self-center align-middle" src={home} />
        </Link>
        <Link
          className="rounded-md  m-1 w-1/6 p-2 bg-slate-400 hover:bg-slate-500"
          to="/app/profile"
        >
          <img className="justify-self-center align-middle" src={user} />
        </Link>
      </nav>
    </>
  );
}
