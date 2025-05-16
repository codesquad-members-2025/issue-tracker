import styles from "./FilterTabButton.module.css";
import dropdownIcon from "../../assets/icons/chevronDown.svg";

function FilterTabButton({ filterName, onClick }) {
  return (
    <>
      <button className={styles.buttonContainer} onClick={onClick}>
        {filterName}
        <img src={dropdownIcon} className={dropdownIcon} />
      </button>
    </>
  );
}

export default FilterTabButton;
