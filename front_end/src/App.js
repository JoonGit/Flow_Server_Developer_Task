import { Routes, Route } from "react-router-dom";
import SignupPage from "./pages/SignupPage";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import "./App.css";

function App() {
  return (
    <Routes>
      {/* <Route path="/signin" element={<SigninPage></SigninPage>} /> */}
      <Route path="/signup" element={<SignupPage></SignupPage>} />
      <Route path="/main" element={<MainPage></MainPage>} />
      <Route path="/" element={<LoginPage></LoginPage>} />
    </Routes>
  );
}

export default App;
