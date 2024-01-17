import { IShelfType } from 'app/shared/model/shelf-type.model';

export interface IShelf {
  id?: number;
  serialNumber?: string | null;
  digitNumber?: string | null;
  info?: string | null;
  shelfType?: IShelfType | null;
}

export const defaultValue: Readonly<IShelf> = {};
