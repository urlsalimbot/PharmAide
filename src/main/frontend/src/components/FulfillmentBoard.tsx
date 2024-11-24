export default function FulfillmentBoard() {
  return (
    <div className="p-2 m-2">
      <span className="block">FulfillmentBoard</span>

      <table className="border-green-900 border-2 items-center justify-start w-[100%]">
        <tr className="">
          <th className="border-black border-b-2">Transaction id</th>
          <th className="border-black border-b-2">Summary</th>
          <th className="border-black border-b-2">Actions</th>
        </tr>
      </table>
    </div>
  );
}
