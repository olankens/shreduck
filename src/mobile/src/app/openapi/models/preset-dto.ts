/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { PresetExerciseDto } from '../models/preset-exercise-dto';
export interface PresetDto {
  id?: number;
  memberId?: number;
  name?: string;
  presetExercises?: Array<PresetExerciseDto>;
  presetType?: 'AD' | 'FREE' | 'PAID';
}
