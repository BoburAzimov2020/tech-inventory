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
import { ICabelType } from 'app/shared/model/cabel-type.model';
import { getEntity, updateEntity, createEntity, reset } from './cabel-type.reducer';

export const CabelTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const obyekts = useAppSelector(state => state.obyekt.entities);
  const cabelTypeEntity = useAppSelector(state => state.cabelType.entity);
  const loading = useAppSelector(state => state.cabelType.loading);
  const updating = useAppSelector(state => state.cabelType.updating);
  const updateSuccess = useAppSelector(state => state.cabelType.updateSuccess);

  const handleClose = () => {
    navigate('/cabel-type' + location.search);
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
      ...cabelTypeEntity,
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
          ...cabelTypeEntity,
          obyekt: cabelTypeEntity?.obyekt?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.cabelType.home.createOrEditLabel" data-cy="CabelTypeCreateUpdateHeading">
            Создать или отредактировать Cabel Type
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="cabel-type-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="cabel-type-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField
                label="Info"
                id="cabel-type-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="cabel-type-obyekt" name="obyekt" data-cy="obyekt" label="Obyekt" type="select">
                <option value="" key="0" />
                {obyekts
                  ? obyekts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cabel-type" replace color="info">
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

export default CabelTypeUpdate;
