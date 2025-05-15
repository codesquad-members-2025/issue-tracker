import styles from "./PopupList.module.css";
import checkOffIcon from "../../assets/icons/checkOffCircle.svg";

function PopupList({ filterName, actionLocation }) {
  return (
    <div className={styles.dropdownPanel}>
      <div className={styles.dropdownPanelTitle}>
        {actionLocation === "filterBar"
          ? `${filterName} 필터`
          : `${filterName} 정렬`}
      </div>
      <div className={styles.dropdownPanelOptions}>
        {actionLocation === "filterBar" ? (
          <button className={styles.option}>
            <span>{filterName} 없는 이슈</span>
            <img src={checkOffIcon} alt="checkOff" />
          </button>
        ) : (
          ""
        )}
        {/*TODO 받아온 데이터 기준으로 map 사용하여 추후에 수정할 예정*/}
        <button className={styles.option}>
          <div className={styles.userInfo}>
            <div className={styles.userImage} />
            <span>samsami9</span>
          </div>
          <img src={checkOffIcon} className={checkOffIcon} alt="checkOff" />
        </button>
        <button className={styles.option}>
          <div className={styles.userInfo}>
            <div className={styles.userImage} />
            <span>jicho</span>
          </div>
          <img src={checkOffIcon} className={checkOffIcon} alt="checkOff" />
        </button>
      </div>
    </div>
  );
}

export default PopupList;
