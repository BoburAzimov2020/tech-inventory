import { IBuyurtmaRaqam } from 'app/shared/model/buyurtma-raqam.model';

export interface IObyekt {
  id?: number;
  name?: string;
  home?: string | null;
  street?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  info?: string | null;
  buyurtmaRaqam?: IBuyurtmaRaqam | null;
}

export const defaultValue: Readonly<IObyekt> = {};
