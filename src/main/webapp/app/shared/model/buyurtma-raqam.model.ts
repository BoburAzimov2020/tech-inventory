import { ILoyiha } from 'app/shared/model/loyiha.model';

export interface IBuyurtmaRaqam {
  id?: number;
  name?: string | null;
  numberOfOrder?: string | null;
  loyiha?: ILoyiha | null;
}

export const defaultValue: Readonly<IBuyurtmaRaqam> = {};
