import FulfillmentBoard from "../components/FulfillmentBoard";
import PieSales from "../components/PieSales";
import RevenueGraph from "../components/RevenueGraph";
import StocksBarGraph from "../components/StocksBarGraph";
import useAuth from "../hooks/useAuth";

export default function Admin() {
  const { auth } = useAuth();
  return (
    <>
      <span className="block text-xl ml-2 px-3 pt-2  font-serif ">
        Dashboard for {auth.username}
      </span>
      <div className="m-2 p-2 grid-rows-2 grid-cols-3 grid gap-4 align-bottom bg-zinc-300">
        <div>
          <PieSales />
        </div>
        <div>
          <StocksBarGraph />
        </div>
        <div className="bg-slate-200 rounded-md row-span-2">
          <FulfillmentBoard />
        </div>
        <div className=" col-span-2">
          <RevenueGraph />
        </div>
      </div>
    </>
  );
}
