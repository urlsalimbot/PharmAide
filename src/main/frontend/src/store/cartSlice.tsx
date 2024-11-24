import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "./store";
import { Product } from "../interface/product";

export interface orderProd extends Product {
  quantity: number;
  storeprod_id: string;
}

interface cartState {
  selProducts: orderProd[];
  cartTab: boolean;
}

const initialState: cartState = {
  selProducts: [],
  cartTab: false,
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart(state, action: PayloadAction<orderProd>) {
      const { storeprod_id, quantity, ...props } = action.payload;
      const indexProductId = state.selProducts.findIndex(
        (item) => item.storeprod_id === storeprod_id
      );
      if (indexProductId >= 0) {
        state.selProducts[indexProductId].quantity += quantity;
      } else {
        state.selProducts.push({
          storeprod_id,
          quantity,
          ...props,
        });
      }
    },
    changeQuantity(state, action: PayloadAction<orderProd>) {
      const { storeprod_id, quantity } = action.payload;
      const indexProductId = state.selProducts.findIndex(
        (item) => item.storeprod_id === storeprod_id
      );
      if (quantity > 0) {
        state.selProducts[indexProductId].quantity = quantity;
      } else {
        // delete state.selProducts[indexProductId];
        state.selProducts = state.selProducts.filter(
          (item) => item.storeprod_id !== storeprod_id
        );
      }
    },
    toggleCartTab(state) {
      if (state.cartTab === false) {
        state.cartTab = true;
      } else {
        state.cartTab = false;
      }
    },
    clearSelProducts(state) {
      state.selProducts = [];
    },
  },
});

export const { addToCart, changeQuantity, toggleCartTab, clearSelProducts } =
  cartSlice.actions;

export const selectCart = (state: RootState) => state.cart.selProducts;

export default cartSlice.reducer;
