import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IObyekt } from 'app/shared/model/obyekt.model';
import { getEntities as getObyekts } from 'app/entities/obyekt/obyekt.reducer';
import { IAvtomat } from 'app/shared/model/avtomat.model';
import { getEntity, updateEntity, createEntity, reset } from './avtomat.reducer';

export const AvtomatUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const obyekts = useAppSelector(state => state.obyekt.entities);
  const avtomatEntity = useAppSelector(state => state.avtomat.entity);
  const loading = useAppSelector(state => state.avtomat.loading);
  const updating = useAppSelector(state => state.avtomat.updating);
  const updateSuccess = useAppSelector(state => state.avtomat.updateSuccess);

  const handleClose = () => {
    navigate('/avtomat' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getObyekts({}));
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
      ...avtomatEntity,
      ...values,
      obyekt: obyekts.find(it => it.id.toString() === values.obyekt.toString()),
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
          ...avtomatEntity,
          obyekt: avtomatEntity?.obyekt?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.avtomat.home.createOrEditLabel" data-cy="AvtomatCreateUpdateHeading">
            Создать или отредактировать Avtomat
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="avtomat-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="avtomat-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField label="Model" id="avtomat-model" name="model" data-cy="model" type="text" />
              <ValidatedField label="Group" id="avtomat-group" name="group" data-cy="group" type="text" />
              <ValidatedField
                label="Info"
                id="avtomat-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="avtomat-obyekt" name="obyekt" data-cy="obyekt" label="Obyekt" type="select">
                <option value="" key="0" />
                {obyekts
                  ? obyekts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/avtomat" replace color="info">
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

export default AvtomatUpdate;
