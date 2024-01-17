import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjectorType } from 'app/shared/model/projector-type.model';
import { getEntities as getProjectorTypes } from 'app/entities/projector-type/projector-type.reducer';
import { IProjector } from 'app/shared/model/projector.model';
import { getEntity, updateEntity, createEntity, reset } from './projector.reducer';

export const ProjectorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projectorTypes = useAppSelector(state => state.projectorType.entities);
  const projectorEntity = useAppSelector(state => state.projector.entity);
  const loading = useAppSelector(state => state.projector.loading);
  const updating = useAppSelector(state => state.projector.updating);
  const updateSuccess = useAppSelector(state => state.projector.updateSuccess);

  const handleClose = () => {
    navigate('/projector' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjectorTypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...projectorEntity,
      ...values,
      projectorType: projectorTypes.find(it => it.id.toString() === values.projectorType.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...projectorEntity,
          projectorType: projectorEntity?.projectorType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.projector.home.createOrEditLabel" data-cy="ProjectorCreateUpdateHeading">
            Создать или отредактировать Projector
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="projector-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="projector-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField label="Model" id="projector-model" name="model" data-cy="model" type="text" />
              <ValidatedField
                label="Info"
                id="projector-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField
                id="projector-projectorType"
                name="projectorType"
                data-cy="projectorType"
                label="Projector Type"
                type="select"
              >
                <option value="" key="0" />
                {projectorTypes
                  ? projectorTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projector" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Назад</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Сохранить
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProjectorUpdate;
