import { useState } from "react";

import styles from "./IssueTable.module.css";
import "../../styles/tokens.css";
import checkBoxInitialIcon from "../../assets/icons/checkBoxInitial.svg";

import OpenIssuePage from "./OpenIssuePage";
import ClosedIssuePage from "./ClosedIssuePage";

// 사용자가 클릭한 탭에 따라 스타일을 변경
// 탭을 클릭할 경우 해당 탭이 활성화되고, 다른 탭은 비활성화
const getStyleTab = (state, isOpen) => {
  // state와 isOpen이 같으면 selectedTab 스타일 추가
  // state와 isOpen이 다르면 selectedTab 스타일 제거
  return state === isOpen ? `${styles.selectedTab}` : ``;
};

function IssueTable() {
  const [isOpen, setIsOpen] = useState("open");
  return (
    <div className={styles.issueTableContainer}>
      <div className={styles.IssueViewControls}>
        <button>
          <img src={checkBoxInitialIcon} alt="checkbox" />
        </button>
        <div className={styles.issueTabs}>
          <button
            className={`${getStyleTab("open", isOpen)} ${styles.tabButton}`}
            onClick={() => setIsOpen("open")}
          >
            <div className={styles.openIssueIcon}></div>
            열린 이슈
          </button>
          <button
            className={`${getStyleTab("closed", isOpen)} ${styles.tabButton}`}
            onClick={() => setIsOpen("closed")}
          >
            <div className={styles.closedIssueIcon}></div>
            닫힌 이슈
          </button>
        </div>
      </div>

      <div className={styles.issueListContainer}>
        {isOpen === "open" && <OpenIssuePage isOpen={isOpen} />}
        {isOpen === "closed" && <ClosedIssuePage />}
      </div>
    </div>
  );
}

export default IssueTable;
