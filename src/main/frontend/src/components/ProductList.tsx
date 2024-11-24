import { Product } from "../interface/product";
import shopcart from "../assets/shopping-cart.svg";
import { useAppDispatch } from "../hooks/reduxHooks";
import { addToCart } from "../store/cartSlice";

interface props {
  filteredProductList: Array<Product>;
  loading: boolean;
}

const ProductList = ({ filteredProductList, loading }: props) => {
  const dispatch = useAppDispatch();

  const handleClick = (prod: Product) => {
    dispatch(
      addToCart({
        id: prod.id,
        brandname: prod.brandname,
        genericname: prod.genericname,
        store_id: prod.store_id,
        price: prod.price,
        category: prod.category,
        quantity: 1,
        storeprod_id: prod.id + prod.store_id,
      })
    );
  };

  return loading ? (
    <>Loading</> // use your loading state or component
  ) : (
    <div className="w-full h-auto px-5">
      <div className="w-full pt-2 font-serif">Products: </div>
      <div className="w-full h-full font-serif flex flex-wrap gap-1 justify-around items-start overflow-x-auto">
        {filteredProductList.map((product: Product) => (
          <div
            key={product.id + product.store_id}
            className="w-[19%] h-fit my-3 rounded-xl overflow-hidden border bg-white border-gray-200"
          >
            <div className="mt-3 mb-2 px-3">
              <h2 className="text-xl">{product.brandname}</h2>
              <h3>{product.genericname}</h3>
              <div>
                Price: ${product.price} ---- sold by: {product.store_id}
              </div>
            </div>

            <div className="mt-2 mb-2 px-3">
              <button
                onClick={() => handleClick(product)}
                className="p-1 rounded-md inline-flex text-sm text-gray-600 bg-slate-300"
              >
                <img className="" src={shopcart} />{" "}
                <p className="px-2">add to cart</p>
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductList;
