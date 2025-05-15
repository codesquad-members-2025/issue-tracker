import { useState } from "react";
import styles from "./WriteIssue.module.css";
import IssueTable from "./IssueTable";
import ControlHeader from "../ControlHeader/ControlHeader";
import PopupList from "../common/PopupList";
import { API_URL } from "../../constants/link";

const handleClick = (title, content, file) => {
  const issueData = {
    title: title,
    content: content,
    author: 46,
  };

  // TODO 추후 받아온 file 처리 예정
  const fileData = file;

  const formData = new FormData();
  formData.append("issue", JSON.stringify(issueData));

  if (fileData && fileData.length > 0) {
    fileData.forEach((file) => formData.append("file", file));
  }
  for (let [key, value] of formData.entries()) {
    console.log(`${key}:`, value);
  }

  fetch(`${API_URL}/api/issues`, {
    method: "POST",
    body: formData,
  })
    .then((res) => res.json())
    .then((data) => console.log("서버 응답:", data))
    .catch((err) => console.error("에러:", err));
};

// TODO 컴포넌트 분리
function WriteIssue({ setWriteIssue }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [selectedcancelButton, setselectedcancelButton] = useState(false);
  const [file, setFile] = useState([]);
  const [activeFilter, setActiveFilter] = useState(null);

  const handleClickcancelButton = (isSelected) => {
    setselectedcancelButton(isSelected);
    setWriteIssue(false);
  };

  const handleFileChange = (e) => {
    setFile(Array.from(e.target.files));
  };

  const handleFilterClick = (filterName) => {
    setActiveFilter((prev) => (prev === filterName ? null : filterName));
  };

  if (selectedcancelButton) {
    return (
      <>
        <ControlHeader />
        <IssueTable />
      </>
    );
  }

  return (
    <>
      <div className={styles.writeIssueContainer}>
        <h3 className={styles.headerName}>새로운 이슈 작성</h3>
        <hr />

        <div className={styles.inputArea}>
          <div className={styles.userImage} />
          <div className={styles.inputContainer}>
            <div className={styles.inputTitleContainer}>
              <input
                type="text"
                placeholder="제목"
                className={styles.issueTitleInput}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>
            <div className={styles.issueContentInputContainter}>
              <textarea
                placeholder="코멘트를 입력하세요"
                className={styles.issueContentInput}
                onChange={(e) => setContent(e.target.value)}
              />
              <input type="file" multiple onChange={handleFileChange} />
            </div>
          </div>
          <div className={styles.filtersContainer}>
            {["담당자", "레이블", "마일스톤"].map((filter) => {
              return (
                <div
                  key={filter}
                  className={styles.filterButton}
                  onClick={() => handleFilterClick(filter)}
                >
                  <span className={styles.filterButtonTitle}>
                    {filter}
                    {activeFilter === filter && (
                      <PopupList
                        filterName={filter}
                        className={styles.activeFilter}
                      />
                    )}
                  </span>
                  <div className={styles.plusIcon} />
                </div>
              );
            })}
          </div>
        </div>

        <hr />
        <div className={styles.buttonArea}>
          <button
            className={styles.cancelButton}
            onClick={() => handleClickcancelButton(true)}
          >
            작성 취소
          </button>
          <button
            className={styles.submitButton}
            onClick={() => handleClick(title, content, file)}
          >
            완료
          </button>
        </div>
      </div>
    </>
  );
}

export default WriteIssue;
