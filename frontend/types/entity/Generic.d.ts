interface Generic<T extends GenericAudit> {
  id: number | undefined;
  version: number;
  modifiedBy: string;
  modifiedAt: string;
  versions: T[];
}
