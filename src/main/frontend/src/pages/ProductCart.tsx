import { useAppSelector, useAppDispatch } from "../hooks/reduxHooks";
import shopcart from "../assets/shopping-cart.svg";
import { clearSelProducts, toggleCartTab } from "../store/cartSlice";
import { CartItems } from "../components/CartItems";
import api from "../api/axios";
import { useEffect, useState } from "react";
import { AxiosError } from "axios";
import { SaleDTO, TransactionDTO } from "../interface/product";
import useAuth from "../hooks/useAuth";

const TRANSACTION_API = "app/transactions/";

export const ProductCart = () => {
  const { auth } = useAuth();
  const [subTotal, setSubTotal] = useState(0);
  const [payment, setPayment] = useState<number>(0);
  const cart = useAppSelector((store) => store.cart.selProducts);
  const cartTab = useAppSelector((store) => store.cart.cartTab);
  const dispatch = useAppDispatch();

  console.log(cartTab);
  console.log(cart);

  const handleClick = () => {
    dispatch(toggleCartTab());
  };

  const handleCheckOut = () => {
    const finalprods = cart;
    // console.log(JSON.stringify({ finalprods, subTotal, payment }));
    const sales: SaleDTO[] = [];
    finalprods.forEach((element) => {
      const sale: SaleDTO = {
        id: element.id,
        name: element.brandname,
        quantity: element.quantity,
      };
      sales.push(sale);
    });

    const storeid = finalprods[0].store_id;

    const finalTransac: TransactionDTO = {
      store_id: storeid,
      buyer_id: auth.username,
      total: subTotal,
      payment: payment,
      sales: sales,
    };

    // console.log(
    //   JSON.stringify({
    //     store_id: storeid,
    //     buyer_id: auth.username,
    //     total: subTotal,
    //     payment: payment,
    //     sales: sales,
    //   })
    // );
    processTransac(finalTransac);
    dispatch(clearSelProducts());
  };

  const handleToggleCart = () => {
    dispatch(toggleCartTab());
  };

  const processTransac = async (transac: TransactionDTO) => {
    try {
      const response = await api().post(
        TRANSACTION_API,
        JSON.stringify({
          store_id: transac.store_id,
          buyer_id: transac.buyer_id,
          total: transac.total,
          payment: transac.payment,
          sales: transac.sales,
        }),
        {
          headers: { "Content-Type": "appliction/json" },
        }
      );

      console.log(JSON.stringify(response.data));
    } catch (err) {
      if (err instanceof AxiosError) console.log(err);
      else console.log(err);
    }
  };

  useEffect(() => {
    let sub = 0;
    cart.forEach((item) => {
      sub += item.quantity * item.price;
    });
    setSubTotal(sub);
  }, [cart]);

  return (
    <>
      <div
        className={`font-serif fixed z-30 h-full w-1/3 -inset-0 bg-slate-500
        transition-transform 
        transform ${cartTab ? "" : "-translate-x-full"}`}
      >
        <div className="flex items-center " />
        <h3 className="-left-2 flex justify-center text-white">Cart Items</h3>
        <div className="mx-2 ml-1 rounded-tr-md rounded-tl-md bg-slate-200 h-[60%] overflow-auto">
          {cart !== undefined ? (
            <div className="w-full flex flex-col gap-1 p-2">
              {cart.map((item) => (
                <CartItems props={item} />
              ))}
            </div>
          ) : (
            <p>nothing here yet</p>
          )}
        </div>
        <div className="mr-2 overflow-hidden rounded-bl-md rounded-br-md mx-1">
          <div className="bg-slate-500 w-full">
            <p className="text-red-500">Total: ${subTotal}</p>
            <input
              className="w-full rounded-t-md pl-6"
              type="number"
              placeholder="$0.00"
              onChange={(e) => setPayment(parseFloat(e.target.value))}
            />
          </div>
          <button onClick={handleClick} className="p-2 bg-yellow-300 w-1/2">
            close
          </button>
          <button onClick={handleCheckOut} className="p-2 bg-red-500 w-1/2">
            checkout
          </button>
        </div>
      </div>
      <button
        onClick={handleToggleCart}
        className="fixed bottom-0 z-40 bg-green-500 rounded-full left-4 p-3"
      >
        <img className=" justify-self-center" src={shopcart} />
      </button>
    </>
  );
};
