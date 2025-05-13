import styles from "./TableHeader.module.css";
import IssueTabButton from "../common/IssueTabButton";

function TableHeader({ isOpen, setIsOpen, issueCount }) {
  return (
    <div className={styles.tableHeader}>
      <div className={styles.issueViewControls}>
        <button className={styles.checkbox} />

        <div className={styles.issueTabs}>
          <IssueTabButton
            isActive={isOpen === true}
            onClick={() => setIsOpen(true)}
            iconClassName="openIssueIcon"
            issueName={`열린 이슈(${issueCount.openCount})`}
          />
          <IssueTabButton
            isActive={isOpen === false}
            onClick={() => setIsOpen(false)}
            iconClassName="closedIssueIcon"
            issueName={`닫힌 이슈(${issueCount.closedCount})`}
          />
        </div>
      </div>
    </div>
  );
}

export default TableHeader;
