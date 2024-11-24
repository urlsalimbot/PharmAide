import { Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import user from "../assets/user.svg";

export default function Profile() {
  const { auth, setAuth } = useAuth();

  const handleLogOut = () => {
    setAuth({ username: "", role: "", loggedin: false });
  };

  return (
    <div>
      {auth.role !== "CUSTOMER" && (
        <Link
          className="text-slate-200 font-serif hover:bg-slate-400 fixed p-2 m-5 rounded-md bg-slate-600"
          to="..\admin"
          relative="path"
        >
          go to Dashboard
        </Link>
      )}
      <button
        onClick={handleLogOut}
        className="text-slate-200 font-serif hover:bg-slate-400 fixed right-8 p-2 m-5 rounded-md bg-slate-600"
      >
        Log Out
      </button>
      <div className="text-center justify-center flex-col absolute rounded-2xl left-[2%] top-20 translate-x-[33%] p-2 w-56 h-56 bg-slate-500 font-serif inset-5">
        <img
          src={user}
          alt=""
          className="justify-self-center h-[90%] w-[90%]"
        />
        <div className="relative bottom-6">
          <p>{auth.username}</p>
          <p>{auth.role}</p>
        </div>
      </div>
      <div className="justify-self-center align-middle p-2 w-28 h-60 bg-slate-500"></div>
    </div>
  );
}
