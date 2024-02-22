interface APIError {
  code: string;
  message: string;
  path: string;
  timestamp: string;
  status: number;
  apiSubErrors: APISubError[];
}
