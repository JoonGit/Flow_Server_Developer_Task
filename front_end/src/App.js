import logo from "./logo.svg";
import { Routes, Route } from "react-router-dom";
import SignupPage from "./pages/SignupPage";
import SigninPage from "./pages/SigninPage";
import MainPage from "./pages/MainPage";
import "./App.css";

function App() {
  return (
    <Routes>
      {/* <Route path="/signin" element={<SigninPage></SigninPage>} /> */}
      <Route path="/signup" element={<SignupPage></SignupPage>} />
      <Route path="/main" element={<MainPage></MainPage>} />
      <Route path="/" element={<SigninPage></SigninPage>} />
    </Routes>
  );
}

export default App;
