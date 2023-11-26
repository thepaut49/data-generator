// interface Generic<T extends GenericAudit> {
//   id: number | undefined;
//   version: number;
//   modifiedBy: string;
//   modifiedAt: string;
//   versions: T[];
// }

import { GenericAudit } from "./GenericAudi";

export class Generic<T extends GenericAudit> {
  id: number | undefined;
  version: number;
  modifiedBy: string;
  modifiedAt: string;
  versions: T[];
  constructor() {
    this.id = -1;
    this.version = -1;
    this.modifiedBy = "";
    this.modifiedAt = "";
    this.versions = [];
  }
}
