import styles from "./ControlHeader.module.css";

function ControlHeader({ onClick }) {
  return (
    <>
      <button onClick={onClick} className={styles.writeIssueButton}>
        이슈 작성
      </button>
    </>
  );
}

export default ControlHeader;
