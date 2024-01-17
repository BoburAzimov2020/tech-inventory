import { IObyekt } from 'app/shared/model/obyekt.model';

export interface IAttachment {
  id?: number;
  path?: string | null;
  originalFileName?: string | null;
  fileName?: string | null;
  contentType?: string | null;
  fileSize?: string | null;
  info?: string | null;
  obyekt?: IObyekt | null;
}

export const defaultValue: Readonly<IAttachment> = {};
