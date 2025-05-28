export interface MilestoneShort {
  id: number;
  title: string;
}

export interface MilestoneDetail extends MilestoneShort {
  totalIssueCount: number;
  closedIssueCount: number;
}

export interface GetMilestonesShortResponse {
  milestones: MilestoneShort[];
}

export interface GetMilestonesResponse {
  milestones: MilestoneDetail[];
}
