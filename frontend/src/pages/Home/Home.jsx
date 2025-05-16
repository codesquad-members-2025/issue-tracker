import { useState } from "react";
import Header from "../../components/Header/Header";
import IssueTable from "../../components/Dashboard/IssueTable";
import ControlHeader from "../../components/ControlHeader/ControlHeader";
import WriteIssue from "../../components/Dashboard/WriteIssue";

function Home() {
  const [writeIssue, setWriteIssue] = useState(false);

  return (
    <>
      <Header />
      {!writeIssue ? <ControlHeader onClick={() => setWriteIssue(true)} /> : ""}
      {writeIssue ? (
        <WriteIssue setWriteIssue={setWriteIssue} />
      ) : (
        <IssueTable />
      )}
    </>
  );
}

export default Home;
