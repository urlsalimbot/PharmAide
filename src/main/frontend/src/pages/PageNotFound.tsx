import { Link } from "react-router-dom";

export default function PageNotFound() {
  return (
    <div className="w-full h-full flex justify-center align font-serif items-center">
      <div className="flex justify-center flex-col items-start">
        <h1 className="text-3xl font-serif items-center">
          Page Not Found bruh
        </h1>
        <Link className="p-1 m-1 rounded-md bg-slate-300 w-1/2" to="..\">
          Back to Login
        </Link>
        <Link className="p-1 m-1 rounded-md bg-slate-300 w-1/2" to="..\app">
          Back to App
        </Link>
      </div>
    </div>
  );
}
