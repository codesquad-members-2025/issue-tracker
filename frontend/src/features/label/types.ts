export interface Label {
  id: number;
  name: string;
  description: string | null;
  color: string;
}

export interface GetLabelsResponse {
  labels: Label[];
}
