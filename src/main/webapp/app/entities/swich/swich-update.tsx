import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISwichType } from 'app/shared/model/swich-type.model';
import { getEntities as getSwichTypes } from 'app/entities/swich-type/swich-type.reducer';
import { ISwich } from 'app/shared/model/swich.model';
import { getEntity, updateEntity, createEntity, reset } from './swich.reducer';

export const SwichUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const swichTypes = useAppSelector(state => state.swichType.entities);
  const swichEntity = useAppSelector(state => state.swich.entity);
  const loading = useAppSelector(state => state.swich.loading);
  const updating = useAppSelector(state => state.swich.updating);
  const updateSuccess = useAppSelector(state => state.swich.updateSuccess);

  const handleClose = () => {
    navigate('/swich' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSwichTypes({}));
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
      ...swichEntity,
      ...values,
      swichType: swichTypes.find(it => it.id.toString() === values.swichType.toString()),
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
          ...swichEntity,
          swichType: swichEntity?.swichType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.swich.home.createOrEditLabel" data-cy="SwichCreateUpdateHeading">
            Создать или отредактировать Swich
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="swich-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="swich-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField label="Model" id="swich-model" name="model" data-cy="model" type="text" />
              <ValidatedField label="Port Number" id="swich-portNumber" name="portNumber" data-cy="portNumber" type="text" />
              <ValidatedField
                label="Info"
                id="swich-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="swich-swichType" name="swichType" data-cy="swichType" label="Swich Type" type="select">
                <option value="" key="0" />
                {swichTypes
                  ? swichTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/swich" replace color="info">
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

export default SwichUpdate;
