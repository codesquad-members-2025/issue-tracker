import "./styles/reset.css";
import "./styles/tokens.css";
import "./styles/fonts.css";
import Home from "./pages/Home/Home";
import styles from "./App.module.css";

function App() {
  return (
    <div className={styles.wrapperPadding}>
      <Home />
    </div>
  );
}

export default App;
