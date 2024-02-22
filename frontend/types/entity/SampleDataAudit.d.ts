interface SampleDataCategoryAudit extends GenericAudit {
  key: string;
  isBlobTypeValue: boolean;
  value: string;
  blobValue: string;
  categoryId: number | undefined;
  categoryName: string | undefined;
}
