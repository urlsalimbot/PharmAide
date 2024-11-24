import { useState, useEffect, useMemo } from "react";
// import ProductList from "../components/ProductList";
import ProductList from "../components/ProductList";
import { Product } from "../interface/product";
import api from "../api/axios";
import { AxiosError } from "axios";

export default function Market() {
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [productList, setProductList] = useState<Array<Product>>([]);
  const [filteredProductList, setFilteredProductlists] = useState<Product[]>(
    []
  );

  const filteredList = useMemo(() => {
    return productList.filter((item) =>
      Object.values(item).some((value) =>
        value.toString().toLowerCase().includes(search.toLowerCase())
      )
    );
  }, [search]);

  const getProducts = async () => {
    setLoading(true);
    try {
      const response = await api().get("app/store/", {
        headers: { "Content-Type": "application/json" },
      });
      console.log(JSON.stringify(response.data));
      const prods = response?.data;
      setProductList(prods);
      setFilteredProductlists(prods);
      setLoading(false);
    } catch (err) {
      if (err instanceof AxiosError) {
        console.log(err);
      } else {
        console.log(err);
      }
    }
  };
  useEffect(() => {
    console.log("data done loading");
    setFilteredProductlists(filteredList);
  }, [search]);

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <div className="">
      <span className="text-2xl font-serif font-bold inline-flex pt-2 px-4">
        Market
      </span>
      <input
        name="search"
        type="text"
        onChange={(e) => setSearch(e.target.value)}
        placeholder="Search"
        className="px-2 rounded-full inline"
      />
      <div className="">
        <ProductList
          filteredProductList={filteredProductList}
          loading={loading}
        />
      </div>
    </div>
  );
}
