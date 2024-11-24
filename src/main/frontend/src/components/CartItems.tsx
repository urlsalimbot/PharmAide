import { useAppDispatch } from "../hooks/reduxHooks";
import { changeQuantity, orderProd } from "../store/cartSlice";

export const CartItems = ({ props }: { props: orderProd }) => {
  const { quantity, id, ...prop } = props;
  const dispatch = useAppDispatch();

  const handleMinus = () => {
    dispatch(
      changeQuantity({
        id: id,
        quantity: quantity - 1,
        ...prop,
      })
    );
  };
  const handlePlus = () => {
    dispatch(
      changeQuantity({
        id: id,
        quantity: quantity + 1,
        ...prop,
      })
    );
  };

  return (
    <div className="grid-cols-2 w-full p-2 rounded-md bg-slate-300">
      <h3 className="text-xl">
        {prop.brandname} ${prop.price * quantity}
      </h3>
      <h2>
        {prop.genericname} {prop.store_id}
      </h2>
      <div className="inline-flex gap-2">
        <button
          className="rounded-full bg-slate-600 w-5 text-white hover:bg-slate-400"
          onClick={handlePlus}
        >
          +
        </button>
        <p>{quantity}</p>
        <button
          className="rounded-full bg-slate-600 w-5 text-white hover:bg-slate-400"
          onClick={handleMinus}
        >
          -
        </button>
      </div>
    </div>
  );
};
