import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStoykaType } from 'app/shared/model/stoyka-type.model';
import { getEntities as getStoykaTypes } from 'app/entities/stoyka-type/stoyka-type.reducer';
import { IStoyka } from 'app/shared/model/stoyka.model';
import { getEntity, updateEntity, createEntity, reset } from './stoyka.reducer';

export const StoykaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stoykaTypes = useAppSelector(state => state.stoykaType.entities);
  const stoykaEntity = useAppSelector(state => state.stoyka.entity);
  const loading = useAppSelector(state => state.stoyka.loading);
  const updating = useAppSelector(state => state.stoyka.updating);
  const updateSuccess = useAppSelector(state => state.stoyka.updateSuccess);

  const handleClose = () => {
    navigate('/stoyka' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStoykaTypes({}));
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
      ...stoykaEntity,
      ...values,
      stoykaType: stoykaTypes.find(it => it.id.toString() === values.stoykaType.toString()),
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
          ...stoykaEntity,
          stoykaType: stoykaEntity?.stoykaType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.stoyka.home.createOrEditLabel" data-cy="StoykaCreateUpdateHeading">
            Создать или отредактировать Stoyka
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="stoyka-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="stoyka-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField
                label="Info"
                id="stoyka-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="stoyka-stoykaType" name="stoykaType" data-cy="stoykaType" label="Stoyka Type" type="select">
                <option value="" key="0" />
                {stoykaTypes
                  ? stoykaTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/stoyka" replace color="info">
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

export default StoykaUpdate;
