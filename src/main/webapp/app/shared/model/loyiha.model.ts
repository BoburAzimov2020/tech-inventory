import { IObjectTasnifi } from 'app/shared/model/object-tasnifi.model';

export interface ILoyiha {
  id?: number;
  name?: string;
  objectTasnifi?: IObjectTasnifi | null;
}

export const defaultValue: Readonly<ILoyiha> = {};
