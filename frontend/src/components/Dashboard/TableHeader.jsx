import { useState } from "react";
import styles from "./TableHeader.module.css";
import IssueTabButton from "../common/IssueTabButton";
import FilterTabButton from "../common/FilterTabButton";
import PopupList from "../common/PopupList";

function TableHeader({ isOpen, setIsOpen, issueCount }) {
  const [activeFilter, setActiveFilter] = useState(null);

  const handleFilterClick = (filterName) => {
    setActiveFilter((prev) => (prev === filterName ? null : filterName));
  };

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

      <div className={styles.filterBar}>
        {["담당자", "레이블", "마일스톤", "작성자"].map((name) => (
          <div key={name} className={styles.filterTabWrapper}>
            <FilterTabButton
              filterName={name}
              onClick={() => handleFilterClick(name)}
            />
            {activeFilter === name && (
              <PopupList filterName={name} actionLocation={"filterBar"} />
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default TableHeader;
