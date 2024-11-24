export type Product = {
  id: number;
  brandname: string;
  genericname: string;
  store_id: string;
  price: number;
  category: string;
};

export type SaleDTO = {
  id: number;
  name: string;
  quantity: number;
};

export type TransactionDTO = {
  store_id: string;
  buyer_id: string;
  total: number;
  payment: number;
  sales: SaleDTO[];
};
