import styles from "./PopupList.module.css";
import checkOffIcon from "../../assets/icons/checkOffCircle.svg";

function PopupList({ filterName }) {
  return (
    <div className={styles.dropdownPanel}>
      <div className={styles.dropdownPanelTitle}>{filterName} 필터</div>
      <div className={styles.dropdownPanelOptions}>
        <button className={styles.option}>
          <span>{filterName} 없는 이슈</span>
          <img src={checkOffIcon} alt="checkOff" />
        </button>
        <button className={styles.option}>
          <div className={styles.userInfo}>
            <div className={styles.userImage} />
            <span>samsami9</span>
          </div>

          <img src={checkOffIcon} className={checkOffIcon} alt="checkOff" />
        </button>
      </div>
    </div>
  );
}

export default PopupList;
