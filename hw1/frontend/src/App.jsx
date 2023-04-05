import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import TopBar from "./components/TopBar";
import AirPollution from "./components/AirPollution";
import Cache from "./components/Cache";

const App = () => {
  return (
    <Router>
      <TopBar />
      <Routes>
        <Route exact path="/" element={<AirPollution />}></Route>
        <Route exact path="/cache" element={<Cache />}></Route>
      </Routes>
    </Router>
  );
};

export default App;
