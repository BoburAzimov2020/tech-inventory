import { IObjectTasnifiTuri } from 'app/shared/model/object-tasnifi-turi.model';

export interface IObjectTasnifi {
  id?: number;
  name?: string;
  bjectTasnifiTuri?: IObjectTasnifiTuri | null;
}

export const defaultValue: Readonly<IObjectTasnifi> = {};
