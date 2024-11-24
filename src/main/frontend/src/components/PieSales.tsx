import { useEffect } from "react";
import { PieChart, Pie, Tooltip } from "recharts";

type lcdata = {
  name: string;
  value: number;
};

const data01: lcdata[] = [
  {
    name: "Group A",
    value: 400,
  },
  {
    name: "Group B",
    value: 300,
  },
  {
    name: "Group C",
    value: 300,
  },
  {
    name: "Group D",
    value: 200,
  },
  {
    name: "Group E",
    value: 278,
  },
  {
    name: "Group F",
    value: 189,
  },
];

export default function PieSales() {
  useEffect(() => {
    console.log("connect me to serve");
  }, []);

  return (
    <div className="flex-shrink">
      <span>PieSales</span>
      <PieChart width={500} height={400}>
        <Pie
          data={data01}
          dataKey="value"
          nameKey="name"
          cx="50%"
          cy="50%"
          outerRadius={150}
          innerRadius={65}
          fill="#8884d8"
        />
        <Tooltip />
      </PieChart>
    </div>
  );
}
