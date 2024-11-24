import { ReactElement } from "react";

interface props {
  open: boolean;
  onClose: () => void;
  children: ReactElement;
}

export default function Modal({ open, onClose, children }: props) {
  return (
    //bacckdrop
    <div
      onClick={onClose}
      className={`fixed inset-0 z-20 flex 
        justify-center items-center transition-colors 
        ${open ? "visible bg-black/65" : "invisible"}`}
    >
      {/* Modal Body */}

      <div
        onClick={(e) => e.stopPropagation()}
        className={`
    bg-orange-200 rounded-xl shadow p-6 transition-all
    ${open ? "scale-100 opacity-100" : "scale-125 opacity-0"}
    `}
      >
        <button
          onClick={onClose}
          className={`absolute top-2 right-2 p-1 roundd lg 
            text-gray-400 bg-orange-200 hover:bg-gray-50 hover:text-gray-600`}
        >
          X
        </button>
        {children}
      </div>
    </div>
  );
}
