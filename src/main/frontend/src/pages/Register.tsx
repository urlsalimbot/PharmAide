import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { z, ZodType } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

type Profile = {
  //Form Data
  username: string;
  age: number;
  phone: string;
  email: string;
  password: string;
};

const UserSchema: ZodType<Profile> = z
  .object({
    username: z.string(),
    age: z.preprocess(
      (a) => parseInt(z.string().parse(a), 10),
      z.number().gte(18, "Must be 18 and above")
    ),
    email: z.string().email(),
    phone: z.string(),
    password: z
      .string()
      .min(8, { message: "Password is too short" })
      .max(20, { message: "Password is too long" }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"], // path of error
  });

type TUserSchema = z.infer<typeof UserSchema>;

export default function Register() {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<TUserSchema>({
    resolver: zodResolver(UserSchema), // Apply the zodResolver
  });

  const onSubmit = async (data: Profile) => {
    console.log("successfully submitted");
    console.log(data);
  };

  return (
    <div className="w-screen h-screen items-center justify-center flex">
      <div className="font-serif flex flex-col w-1/4 min-w-72 h-5/12 min-h-96 rounded-md">
        <form
          className="font-serif flex flex-col gap-3 grid-rows-6 min-w-72 h-5/12 min-h-96 rounded-md bg-slate-300 px-2"
          onSubmit={handleSubmit(onSubmit)}
        >
          <span className=" col-span-2 text-center pt-5 block text-xl font-semibold">
            Register
          </span>
          <input
            type="username"
            placeholder="Username"
            {...register("username")}
            className="rounded-md pl-2"
          />
          {errors.username && (
            <span className="error-message">{errors.username.message}</span>
          )}
          <input
            type="number"
            placeholder="Age"
            {...register("age")}
            className="rounded-md pl-2"
          />
          {errors.age && (
            <span className="error-message">{errors.age.message}</span>
          )}
          <input
            type="email"
            placeholder="Email"
            {...register("email")}
            className="rounded-md pl-2"
          />
          {errors.username && (
            <span className="error-message">{errors.username.message}</span>
          )}
          <input
            type="phone"
            placeholder="Phone Number"
            {...register("phone")}
            className="rounded-md pl-2"
          />
          {errors.phone && (
            <span className="error-message">{errors.phone.message}</span>
          )}
          <select aria-placeholder={"Choose an option"}>
            <option value="RETAILER">Retailer</option>
            <option value="CUSTOMER">Customer</option>
          </select>
          <input
            type="password"
            placeholder="Password"
            {...register("password")}
            className="rounded-md pl-2"
          />
          {errors.password && (
            <span className="error-message">{errors.password.message}</span>
          )}
          <input
            type="password"
            placeholder="Confirm Password"
            {...register("confirmPassword")}
            className="rounded-md pl-2"
          />
          {errors.confirmPassword && (
            <span className="error-message">
              {errors.confirmPassword.message}
            </span>
          )}

          <div className="mt-auto mb-2 flex justify-center">
            <button
              disabled={isSubmitting}
              className=" text-white w-full h-[38px] align-bottom bg-slate-800 rounded-md "
            >
              Register
            </button>
          </div>
        </form>
        <div className="flex justify-center">
          <Link to={"/"} className="mt-2 text-green-400 text-center">
            Already have an account? Sign in
          </Link>
        </div>
      </div>
    </div>
  );
}
