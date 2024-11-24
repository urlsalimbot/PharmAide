import pharmaide from "../assets/pharmaide.svg";

export default function App() {
  return (
    <>
      <div className="z-20 justify-center rounded-full size-[2rem] min-w-12 min-h-12 top-4 left-4 fixed bg-slate-300">
        <img src={pharmaide} className="pt-1 relative" />
      </div>
      <div className="p-2 pt-36 w-full h-full">
        <div className="justify-self-center content-center text-center h-20">
          <img src={pharmaide} />
          <span className="align-middle text-3xl font-serif">PharmAide</span>
        </div>
        <div className="font-serif flex w-1/2 justify-center h-1/2 align-center justify-self-center bg-slate-300 rounded-lg">
          <p className="flex items-center">
            <span className="text-center">Welcome to PharmAide</span>
          </p>
        </div>
      </div>
    </>
  );
}
