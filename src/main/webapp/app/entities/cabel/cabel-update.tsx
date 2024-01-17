import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICabelType } from 'app/shared/model/cabel-type.model';
import { getEntities as getCabelTypes } from 'app/entities/cabel-type/cabel-type.reducer';
import { ICabel } from 'app/shared/model/cabel.model';
import { getEntity, updateEntity, createEntity, reset } from './cabel.reducer';

export const CabelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cabelTypes = useAppSelector(state => state.cabelType.entities);
  const cabelEntity = useAppSelector(state => state.cabel.entity);
  const loading = useAppSelector(state => state.cabel.loading);
  const updating = useAppSelector(state => state.cabel.updating);
  const updateSuccess = useAppSelector(state => state.cabel.updateSuccess);

  const handleClose = () => {
    navigate('/cabel' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCabelTypes({}));
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
      ...cabelEntity,
      ...values,
      cabelType: cabelTypes.find(it => it.id.toString() === values.cabelType.toString()),
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
          ...cabelEntity,
          cabelType: cabelEntity?.cabelType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.cabel.home.createOrEditLabel" data-cy="CabelCreateUpdateHeading">
            Создать или отредактировать Cabel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="cabel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="cabel-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField label="Model" id="cabel-model" name="model" data-cy="model" type="text" />
              <ValidatedField
                label="Info"
                id="cabel-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="cabel-cabelType" name="cabelType" data-cy="cabelType" label="Cabel Type" type="select">
                <option value="" key="0" />
                {cabelTypes
                  ? cabelTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cabel" replace color="info">
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

export default CabelUpdate;
