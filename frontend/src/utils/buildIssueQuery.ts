"use client";

import type { SelectedFilters } from "@/stores/useIssueFilterStore";

export function filtersToSearchParams(filters: SelectedFilters) {
  const params = new URLSearchParams();
  for (const [k, v] of Object.entries(filters)) {
    if (v) {
      params.set(k, v); // 다중 선택이면 v.split(",") 활용
    }
  }
  return params.toString(); // "label=3&assignee=7" 형태
}

export function searchParamsToFilters(search: string): SelectedFilters {
  const params = new URLSearchParams(search);
  return {
    assignee: params.get("assignee") ?? undefined,
    label: params.get("label") ?? undefined,
    milestone: params.get("milestone") ?? undefined,
    writer: params.get("writer") ?? undefined,
  };
}
