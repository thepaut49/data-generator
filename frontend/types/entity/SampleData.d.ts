interface SampleData extends Generic<SampleDataAudit> {
  key: string;
  isBlobTypeValue: boolean;
  value: string;
  blobValue: string;
  categoryId: number | undefined;
  categoryName: string | undefined;
}
